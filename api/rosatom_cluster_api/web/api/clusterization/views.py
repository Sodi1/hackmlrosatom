from aio_pika.patterns import RPC
from fastapi import APIRouter, Depends, Request, status
from fastapi.responses import JSONResponse
from redis.asyncio import ConnectionPool, Redis
from sqlalchemy.ext.asyncio import AsyncSession

from rosatom_cluster_api.services.rabbit.dependencies import get_rmq_channel_pool
from rosatom_cluster_api.services.redis.dependency import get_redis_pool
from rosatom_cluster_api.db.dependencies import get_db_session
from rosatom_cluster_api.web.api.clusterization.dependencies import run_task
from rosatom_cluster_api.web.api.clusterization.schema import (
    ClusterizationRequest,
    ClusterizationResponse,
    ErrorMessage
)

router = APIRouter()


@router.post(
    "/clusterization",
    tags=["clusterization"],
    summary="Кластеризация",
    status_code=status.HTTP_200_OK,
    responses={
        status.HTTP_400_BAD_REQUEST: {"model": ErrorMessage},
        status.HTTP_422_UNPROCESSABLE_ENTITY: {"model": ErrorMessage},
    },
)
async def get_file_id(
    file_id: ClusterizationRequest,
    request: Request,
    redis_pool: ConnectionPool = Depends(get_redis_pool),
    db_session: AsyncSession = Depends(get_db_session),
) -> JSONResponse:
    """
    Получение базовой кластеризации.
    """
    response = await run_task(file_id=file_id.file_id,
                              cluster_type='regular',
                              request=request,
                              redis_pool=redis_pool,
                              db_session=db_session)

    return JSONResponse(
        status_code=status.HTTP_200_OK,
        content=response,
    )


@router.post(
    "/competence",
    tags=["clusterization"],
    summary="Кластеризация по компетенциям",
    status_code=status.HTTP_200_OK,
    responses={
        status.HTTP_400_BAD_REQUEST: {"model": ErrorMessage},
        status.HTTP_422_UNPROCESSABLE_ENTITY: {"model": ErrorMessage},
    },
)
async def get_competence_clasterization(
    qualification_id: int,
    request: Request,
    redis_pool: ConnectionPool = Depends(get_redis_pool),
    db_session: AsyncSession = Depends(get_db_session),
) -> JSONResponse:
    """
    Получение кластеризации.
    """
    response = await run_task(file_id=qualification_id,
                              cluster_type='competence',
                              request=request,
                              redis_pool=redis_pool,
                              db_session=db_session)

    return JSONResponse(
        status_code=status.HTTP_200_OK,
        content=response,
    )
