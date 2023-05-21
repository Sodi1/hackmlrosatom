from typing import Any, Dict
import json

import numpy as np
import pandas as pd
from sklearn.cluster import DBSCAN
from sklearn.decomposition import PCA
import datetime


def process_data(data):
    df = pd.DataFrame(data, columns = ['ФИО',
                                      'Пол',
                                      'Роль в мероприятии',
                                      'Список компетенций',
                                      'Должность',
                                      'Категория',
                                      'Начало трудового стажа',
                                      'Место работы',
                                      'Профессия',
                                      'Дата рождения',
                                      'Начало трудовой деятельности в РОСАТОМ',
                                      'Образование',
                                      'Место образования',
                                      'Год оканчания',
                                      'Специальность'])
    now = datetime.datetime.now().date()
    df['Дата рождения'] = pd.to_datetime(df['Дата рождения'], errors='coerce')
    df['Дата рождения']=df['Дата рождения'].dt.date
    df['Начало трудового стажа']=pd.to_datetime(df['Начало трудового стажа'], errors='coerce').dt.date
    df['Начало трудовой деятельности в РОСАТОМ']=pd.to_datetime(df['Начало трудовой деятельности в РОСАТОМ'], errors='coerce').dt.date
    df['Возраст'] = 0
    df['Стаж'] = 0
    df['Стаж в РосАтом'] = 0
    df['Возраст'] = df["Дата рождения"].apply(lambda x: now.year - x.year)
    df['Стаж'] = df["Начало трудового стажа"].apply(lambda x: now.year - x.year)
    df['Стаж в РосАтом'] = df["Начало трудовой деятельности в РОСАТОМ"].apply(lambda x: now.year - x.year)
    
    df=df.fillna(0)
    #print(df.columns.tolist())
    
    #print(df)
    
    features_cluster=['Стаж в РосАтом','Возраст','Стаж']
    
    dbscan = DBSCAN(eps=1.6, min_samples=3).fit(df[features_cluster])
    labels = dbscan.labels_
    df['cluster'] = dbscan.labels_
    
    features_cluster.append('cluster')
    
    
    response = {
      'x': features_cluster[0],
      'y': features_cluster[1],
      'z': features_cluster[2],
      'datas': [
            [
                [x, y, z] for
                x, y, z in  zip(df[features_cluster[0]][df['cluster'] == cluster].to_list(),
                                df[features_cluster[1]][df['cluster'] == cluster].to_list(),
                                df[features_cluster[2]][df['cluster'] == cluster].to_list())
                #[x,y,z] for x,y,z in zip(df[features_cluster[0]].to_list(), df[features_cluster[1]].to_list(), df[features_cluster[2]].to_list())
            ] for cluster in df['cluster'].unique().tolist()
        ]
    }
    
    return response
    #return df[features_cluster].to_dict()


def process_competence(uid, data, competence_data):
    df = pd.DataFrame(data, columns = ['ФИО',
                                      'Пол',
                                      'Роль в мероприятии',
                                      'Список компетенций',
                                      'Должность',
                                      'Категория',
                                      'Начало трудового стажа',
                                      'Место работы',
                                      'Профессия',
                                      'Дата рождения',
                                      'Начало трудовой деятельности в РОСАТОМ',
                                      'Образование',
                                      'Место образования',
                                      'Год оканчания',
                                      'Специальность'])
                                      
    comp_df = pd.DataFrame(competence_data, columns = ['ID',
                                      'ID квалификации',
                                      'Данные',
                                      'Дата',
                                      'ФИО',
                                      'Пол'])
    comp_df = comp_df[comp_df['ID квалификации'] == uid]
    comp_df['Данные'] = comp_df['Данные'].apply(json.loads)
    
    now = datetime.datetime.now().date()
    df['Дата рождения'] = pd.to_datetime(df['Дата рождения'], errors='coerce')
    df['Дата рождения']=df['Дата рождения'].dt.date
    df['Начало трудового стажа']=pd.to_datetime(df['Начало трудового стажа'], errors='coerce').dt.date
    df['Начало трудовой деятельности в РОСАТОМ']=pd.to_datetime(df['Начало трудовой деятельности в РОСАТОМ'], errors='coerce').dt.date
    df['Возраст'] = 0
    df['Стаж'] = 0
    df['Стаж в РосАтом'] = 0
    df['Возраст'] = df["Дата рождения"].apply(lambda x: now.year - x.year)
    df['Стаж'] = df["Начало трудового стажа"].apply(lambda x: now.year - x.year)
    df['Стаж в РосАтом'] = df["Начало трудовой деятельности в РОСАТОМ"].apply(lambda x: now.year - x.year)
    
    df = df.merge(comp_df, on='ФИО', how='left')
    
    df = df.dropna(subset=['Данные'])
    
    df = df.fillna(0)
    
    df['Баллы'] = df["Данные"].apply(lambda x: float(x['Баллы, %'].replace(',', '.')))
    
    #print(df.columns.tolist())
    
    #print(df)
    
    features_cluster=['Стаж в РосАтом','Возраст','Стаж', 'Баллы']
    
    dbscan = DBSCAN(eps=1.6, min_samples=3).fit(df[features_cluster])
    labels = dbscan.labels_
    df['cluster'] = dbscan.labels_
    
    features_cluster.append('cluster')
    
    
    response = {
      'x': features_cluster[0],
      'y': features_cluster[1],
      'z': features_cluster[2],
      'c': features_cluster[3],
      'datas': [
            [
                [x, y, z, c] for
                x, y, z, c in  zip(df[features_cluster[0]][df['cluster'] == cluster].to_list(),
                                   df[features_cluster[1]][df['cluster'] == cluster].to_list(),
                                   df[features_cluster[2]][df['cluster'] == cluster].to_list(),
                                   df[features_cluster[3]][df['cluster'] == cluster].to_list())
            ] for cluster in df['cluster'].unique().tolist()
        ]
    }
    
    len(json.dumps(response))
    
    return response
