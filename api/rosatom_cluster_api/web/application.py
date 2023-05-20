from importlib import metadata
from pathlib import Path

from fastapi import FastAPI
from fastapi.responses import UJSONResponse
from fastapi.staticfiles import StaticFiles

from rosatom_cluster_api.web.api.router import api_router
from rosatom_cluster_api.web.lifetime import (
    register_shutdown_event,
    register_startup_event,
)

APP_ROOT = Path(__file__).parent.parent


def get_app() -> FastAPI:
    """
    Get FastAPI application.

    This is the main constructor of an application.

    :return: application.
    """

    tags_metadata = [
    {
        "name": "clusterization",
        "description": "Операции кластеризации.",
    },
    {
        "name": "predict",
        "description": "Прогноз тенденций."
    },
    {
        "name": "system",
        "description": "Операции с API.",
    },
    {
        "name": "metrics",
        "description": "Получение метрик по тенденциям.",
        # "externalDocs": {
        #     "description": "Документация по метрикам",
        #     "url": "https://keras.io/api/metrics/",
        # },
    },
    {
        "name": "settings",
        "description": "Настройка параметров модели прогноза тенденций.",
    },
    ]

    app = FastAPI(
        title="rosatom_cluster_api",
        version=metadata.version("rosatom_cluster_api"),
        docs_url=None,
        redoc_url=None,
        openapi_url="/api/openapi.json",
        default_response_class=UJSONResponse,
    )

    # Adds startup and shutdown events.
    register_startup_event(app)
    register_shutdown_event(app)

    # Main router for the API.
    app.include_router(router=api_router, prefix="/api")
    # Adds static directory.
    # This directory is used to access swagger files.
    app.mount(
        "/static",
        StaticFiles(directory=APP_ROOT / "static"),
        name="static",
    )

    return app
