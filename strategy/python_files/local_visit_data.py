'''
:Author: Yuhong Wu
:Date: 2024-10-03 02:13:02
:LastEditors: Yuhong Wu
:LastEditTime: 2024-10-03 02:34:40
:Description: 
'''
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd


# 源数据地址
DATA_PATH = '../GeoLite2-Country-Blocks-IPv4.csv'
# 数据数量
DATA_AMOUNT = 200000
# 访问次数
VISIT_TIME = 1000000
# 控制冥函数的衰减速率
ALPHA = 10


# 读取GeoLite2-Country-Blocks-IPv4.csv
df = pd.read_csv(DATA_PATH)
# 在network一栏随机选DATA_AMOUNT条数据出来作为数据集
origin_ip_array = df['network'].to_numpy()
choose_ip_array = np.random.choice(origin_ip_array,DATA_AMOUNT,False)
choose_ip_array = choose_ip_array.tolist()

# 生成VISIT_TIME条冥律分布的随机数，它们取值范围为0~100
random_numbers = np.random.power(ALPHA, VISIT_TIME)*100
local_visit_data = []

for i in random_numbers:
    # 将0~100区间分为DATA_AMOUNT个小区间，看当前随机数落在哪一区间内
    index = int(i/(float(100/DATA_AMOUNT)))
    local_visit_data.append(choose_ip_array[index])

# plt.hist(local_visit_data)
# plt.show()

# 将访问记录保存为txt文件
with open('../data/local_visit.txt', 'w') as file:
    for data in local_visit_data:
        file.write(data + '\n')
print(f"{VISIT_TIME}条循环访问记录已生成")