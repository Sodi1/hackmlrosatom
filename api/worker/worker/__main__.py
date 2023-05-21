import asyncio
import json
import os
from datetime import datetime

from aio_pika import connect_robust
from aio_pika.patterns import RPC

from worker.data_processing import process_data, process_competence

from yarl import URL

def clusterization_wrapper(*, cluster_type, uid, data, competence_data):
    if cluster_type == 'regular':
        response = process_data(data)
    elif cluster_type == 'competence':
        response = process_competence(uid, data, competence_data)
    else:
        response = {'status' 'empty request'}
    return response


async def main() -> None:
    """
    Выбор функций для прогноза. Получает сообщения от брокера
    и выполняет нужную операцию (RPC).
    """
    # Variables for RabbitMQ
    # TODO: изменить на выбор через окружение
    rabbit_host: str = "rosatom_cluster_api-rmq"
    rabbit_port: int = 5672
    rabbit_user: str = "guest"
    rabbit_pass: str = "guest"
    rabbit_vhost: str = "/"

    rabbit_url = URL.build(
        scheme="amqp",
        host=rabbit_host,
        port=rabbit_port,
        user=rabbit_user,
        password=rabbit_pass,
        path=rabbit_vhost,
    )

    connection = await connect_robust(
        rabbit_url,
        client_properties={"connection_name": f"worker_{os.getpid()}"},
    )

    # Создание канала (channel)
    channel = await connection.channel()

    rpc = await RPC.create(channel)
    await rpc.register("clusterization_wrapper",
                       clusterization_wrapper,
                       auto_delete=True)
    print('Ready')

    try:
        await asyncio.Future()
    except Exception as e:
        # TODO: внятный обработчик
        print(e)
    finally:
        await connection.close()


if __name__ == "__main__":
    asyncio.run(main())
