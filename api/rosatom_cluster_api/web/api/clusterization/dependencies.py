import json
from hashlib import sha256
from typing import Any, Dict, Literal

from aio_pika.patterns import RPC
from fastapi import Request, status
from fastapi.responses import JSONResponse
from redis.asyncio import ConnectionPool, Redis
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import text

from rosatom_cluster_api.services.rabbit.dependencies import get_rmq_channel_pool
from rosatom_cluster_api.web.api.clusterization.schema import (
    ClusterizationRequest,
    ClusterizationResponse,
    ErrorMessage
)


async def run_task(*,
                   file_id:str,
                   request: Request,
                   redis_pool: ConnectionPool,
                   db_session: AsyncSession) -> ClusterizationResponse:
    # res = await db_session.execute(text('SELECT * FROM rsatom.competence;'))
    res = db_session.execute(text("SELECT c.* FROM rsatom.employee e JOIN "
                                  "rsatom.competence c ON replace("
                                  "(c.data::json->'ФИО')::varchar, '\"', '')"
                                  " = e.full_name"))
    # db_session.commit()
    db_req = [list(row) for row in res.fetchall()]
    print(db_req)
    # Redis cache
    async with Redis(connection_pool=redis_pool) as redis:
        data_hash = sha256(
            json.dumps(db_req).encode(encoding="utf-8")
        ).hexdigest()
        key = f"{file_id}_{data_hash}"
        value = await redis.get(file_id)
        if value is not None:
            response = json.loads(value)
            return response

        channel_pool = get_rmq_channel_pool(request)

        # Проверка на aio_pika.exceptions.MessageProcessError
        async with channel_pool.acquire() as channel:
            rpc = await RPC.create(channel)
            response = await rpc.proxy.clusterization_wrapper(
                data=db_req
            )

        # Save result in cache
        await redis.set(key, json.dumps(response))

    response = ClusterizationResponse(status='ok')

    return response.dict()
    # return db_req
