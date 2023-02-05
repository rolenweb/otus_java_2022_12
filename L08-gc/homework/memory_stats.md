G1

| Memory | Before optimisation | After optimisation |
|--------|---------------------|--------------------|
| 256    | OutOfMemoryError    | msec:3543, sec:3   |
| 512    | msec:15012, sec:15  | msec:2399, sec:2   |
| 1024   | msec:13465, sec:13  | msec:2514, sec:2   |
| 1536   | msec:12104, sec:12  | msec:2860, sec:2   |
| 1792   | msec:11102, sec:11  | msec:2667, sec:2   |
| 2048   | msec:11851, sec:11  | msec:2878, sec:2   |
| 2304   | msec:12451, sec:12  | same               |
| 3072   | msec:12073, sec:12  | same               |
| 4096   | msec:14322, sec:14  | same               |
| 5120   | msec:13408, sec:13  | same               |
| 6144   | msec:12264, sec:12  | same               |
| 7168   | msec:12852, sec:12  | same               |
| 8192   | msec:12911, sec:12  | same               |
| 9216   | msec:13409, sec:13  | same               |
| 10240  | msec:12845, sec:12  | same               |


UseSerialGC

| Memory | Before optimisation | After optimisation |
|--------|---------------------|--------------------|
| 256    | OutOfMemoryError    | msec:4125, sec:4   |
| 512    | msec:17242, sec:17  | msec:3074, sec:3   |
| 1024   | msec:12624, sec:12  | msec:2278, sec:2   |
| 2048   | msec:10544, sec:10  | msec:2195, sec:2   |
| 2560   | msec:9230, sec:9    | msec:2074, sec:2   |
| 3072   | msec:9159, sec:9    | msec:2293, sec:2   |
| 4096   | msec:9045, sec:9    | msec:2196, sec:2   |
| 5120   | msec:7497, sec:7    | msec:2372, sec:2   |
| 6144   | msec:7497, sec:7    | msec:2327, sec:2   |
| 7168   | msec:6313, sec:6    | msec:2268, sec:2   |
| 8192   | msec:6313, sec:6    | msec:2337, sec:2   |
| 9216   | msec:6313, sec:6    | msec:2319, sec:2   |
| 10240  | msec:6313, sec:6    | msec:2315, sec:2   |


UseParallelGC

| Memory | Before optimisation | After optimisation |
|--------|---------------------|--------------------|
| 256    | OutOfMemoryError    | msec:13736, sec:13 |
| 512    | msec:47003, sec:47  | msec:5196, sec:5   |
| 1024   | msec:35145, sec:35  | msec:2184, sec:2   |
| 2048   | msec:25014, sec:25  | msec:1953, sec:1   |
| 3072   | msec:14197, sec:14  | msec:2108, sec:2   |
| 4096   | msec:11572, sec:11  | msec:2183, sec:2   |
| 5120   | msec:9086, sec:9    | msec:2248, sec:2   |
| 6144   | msec:8777, sec:8    | same               |
| 7168   | msec:6624, sec:6    | same               |
| 8192   | msec:6695, sec:6    | same               |
| 9216   | msec:6528, sec:6    | same               |
| 10240  | msec:7082, sec:7    | same               |
| 11264  | msec:6618, sec:6    | same               |

