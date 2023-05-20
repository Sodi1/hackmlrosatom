from sqlalchemy.orm import DeclarativeBase

from rosatom_cluster_api.db.meta import meta


class Base(DeclarativeBase):
    """Base for all models."""

    metadata = meta
