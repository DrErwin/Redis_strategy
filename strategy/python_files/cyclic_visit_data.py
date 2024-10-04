import pandas as pd
import numpy as np

# 源数据地址
DATA_PATH = '../GeoLite2-Country-Blocks-IPv4.csv'
# 数据数量
DATA_AMOUNT = 200000
# 访问次数
VISIT_TIME = 1000000
# 最大循环长度
MAX_CYCLE_LENGTH = 5
# 两次循环访问之间的最大间隔
MAX_CYCLE_INTERVAL = 3
# 生成循环数据的概率
CYCLE_PROBABILITY = 0.1
CYCLE_AGAIN_PROBABILITY = 0.5

# 读取GeoLite2-Country-Blocks-IPv4.csv
df = pd.read_csv(DATA_PATH)
# 在network一栏随机选DATA_AMOUNT条数据出来作为数据集
origin_ip_array = df['network'].to_numpy()
choose_ip_array = np.random.choice(origin_ip_array,DATA_AMOUNT,False)

# 每一项为一条访问数据
cyclic_data = []
# 字典存储循环访问的数据。字典key为"循环起始数据下标 循环终止数据下标"，value为循环间隔
cycle_dict = {}
while(len(cyclic_data) < VISIT_TIME+50):
    # 访问字典，查看有哪些循环数据已经达到循环间隔
    for key in cycle_dict.keys():
        key = str(key)
        if(cycle_dict[key] == 0):
            # 写入达到循环间隔的循环数据
            temp_data = choose_ip_array[int(key.split(" ")[0]): int(key.split(" ")[1])]
            cyclic_data.extend(temp_data)
            if(np.random.random() < CYCLE_AGAIN_PROBABILITY):
                cycle_dict[key] = np.random.randint(1,MAX_CYCLE_INTERVAL) + 1
        # 循环间隔-1
        cycle_dict[key] = cycle_dict[key] - 1

    # 将循环间隔<0的键值对舍弃
    cycle_dict = {key: value for key, value in cycle_dict.items() if value>=0}

    # 随机选择一个数据集内的下标
    index = np.random.randint(0,len(choose_ip_array)-MAX_CYCLE_LENGTH)
    # 随机决定是否生成循环数据
    if(np.random.random() < CYCLE_PROBABILITY):
        # 随机决定循环数据长度
        cycle_length = np.random.randint(1,MAX_CYCLE_LENGTH)
        # 随机决定循环间隔，放入字典中
        cycle_dict[f'{index} {index+cycle_length}'] = np.random.randint(1,MAX_CYCLE_INTERVAL)
        # 写入循环数据
        cyclic_data.extend(choose_ip_array[index: index+cycle_length])
    else:
        # 不循环，写入随机一条数据
        cyclic_data.append(choose_ip_array[index])

cyclic_data = cyclic_data[:VISIT_TIME]

# 将访问记录保存为txt文件
with open('../data/cyclic_visit.txt', 'w') as file:
    for data in cyclic_data:
        file.write(data + '\n')
print(f"{VISIT_TIME}条循环访问记录已生成")