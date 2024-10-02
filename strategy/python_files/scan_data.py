'''
:Author: Yuhong Wu
:Date: 2024-10-03 01:44:32
:LastEditors: Yuhong Wu
:LastEditTime: 2024-10-03 02:06:11
:Description: 
'''
import pandas as pd
import numpy as np

# 源数据地址
DATA_PATH = '../GeoLite2-Country-Blocks-IPv4.csv'
# 数据数量
DATA_AMOUNT = 5000
# 访问次数
VISIT_TIME = 1000000
# 最大循环长度
MIN_CYCLE_LENGTH = 1
# 最大循环长度
MAX_CYCLE_LENGTH = 7
# 生成循环数据的概率
SCAN_PROBABILITY = 0.05

# 读取GeoLite2-Country-Blocks-IPv4.csv
df = pd.read_csv(DATA_PATH)
# 在network一栏随机选DATA_AMOUNT条数据出来作为数据集
origin_ip_array = df['network'].to_numpy()
choose_ip_array = np.random.choice(origin_ip_array,DATA_AMOUNT,False)
choose_ip_array = choose_ip_array.tolist()

# 每一项为一条访问数据
scan_data = []
while(len(scan_data) < VISIT_TIME+50):
    # 随机选择一个数据集内的下标
    index = np.random.randint(0,len(choose_ip_array)-MAX_CYCLE_LENGTH)
    # 随机决定是否生成扫描数据
    if(np.random.random() < SCAN_PROBABILITY):
        # 随机决定扫描数据长度
        scan_length = np.random.randint(MIN_CYCLE_LENGTH,MAX_CYCLE_LENGTH)
        temp_data = []
        # 生成一条扫描数据
        temp_data_1 = choose_ip_array[index : index+scan_length]
        temp_data_2 = temp_data_1[::-1]
        temp_data.extend(temp_data_1)
        temp_data.append(choose_ip_array[index+scan_length+1])
        temp_data.extend(temp_data_2)
        # print(temp_data)
        # print("------------------")
        
        # 写入扫描数据
        scan_data.extend(temp_data)
    else:
        # 不循环，写入随机一条数据
        scan_data.append(choose_ip_array[index])

scan_data = scan_data[:VISIT_TIME]

# 将访问记录保存为txt文件
with open('../data/scan_visit.txt', 'w') as file:
    for data in scan_data:
        file.write(data + '\n')
print(f"{VISIT_TIME}条扫描访问记录已生成")