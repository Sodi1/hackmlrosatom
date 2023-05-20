from fastapi import APIRouter, status
from fastapi.responses import JSONResponse

router = APIRouter()


@router.get(
    "/health",
    tags=["system"],
    summary="Состояние",
    status_code=status.HTTP_200_OK,
)
def health_check() -> None:
    """
    Checks the health of a project.

    It returns 200 if the project is healthy.
    """
    return JSONResponse(status_code=status.HTTP_200_OK, content={'status': 'up'})
