from sklearn.cluster import KMeans
import numpy as np


DATA_PATH = 'data.txt'
data = []
with open(DATA_PATH,'r') as f:
    for line in f.readlines():
        line = line.strip('\n')
        data.append(float(line))
data = np.array(data).reshape(-1,1)
# print(data)
kmeans = KMeans(n_clusters=2, n_init=5)
kmeans.fit(data)
labels = kmeans.labels_
cluster_1 = data[labels == 1]
cluster_2 = data[labels == 0]
min = [np.min(cluster_1), np.min(cluster_2)]
if(min[0] > min[1]):
    print(len(cluster_2))
else:
    print(len(cluster_1))
