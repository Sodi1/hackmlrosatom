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
                   file_id:int,
                   cluster_type:str,
                   request: Request,
                   redis_pool: ConnectionPool,
                   db_session: AsyncSession) -> dict:
    res = ''
    res = await db_session.execute(text('SELECT * FROM rsatom.employee;'))
    competence_res = []
    if cluster_type == 'competence':
            competence_res = await db_session.execute(text('SELECT * FROM rsatom.competence;'))
    db_req = [list(row)[1:-1] for row in res.fetchall()]
    competence_db_req = []
    if cluster_type == 'competence':
        competence_db_req = [list(row)[1:-1] for row in competence_res.fetchall()]
        #print(competence_db_req)

    channel_pool = get_rmq_channel_pool(request)

    # Проверка на aio_pika.exceptions.MessageProcessError
    async with channel_pool.acquire() as channel:
        rpc = await RPC.create(channel)
        try:
            response = await rpc.proxy.clusterization_wrapper(
                cluster_type=cluster_type,
                uid=file_id,
                data=db_req,
                competence_data=competence_db_req
            )
        except:
            return {'status': 'wrong request'}
    # response = ClusterizationResponse(status='ok')

    return response
    #return {'res': db_req[2]}
    #return {'status': 'ok'}
