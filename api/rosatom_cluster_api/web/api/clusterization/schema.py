# TODO: contracts and models

from typing import List, Union
from enum import Enum

from pydantic import BaseModel, Field, validator

class TaskType(Enum):
    clusterization = "clusterization"

    def __str__(self):
        return f"{self.value}"

class ClusterizationRequest(BaseModel):
    file_id: List[int] = Field(
        title="Postgres file ids",
        description="ID файлов пользователей")


class ClusterizationResponse(BaseModel):
    status: str = Field(
        title="Status",
        description="Статус запроса")


class ErrorMessage(BaseModel):
    message: str = Field(
        title="Error message text",
        description="Текст ошибки запроса"
    )
