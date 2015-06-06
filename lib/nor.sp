.subckt gate_0 c b a OUT vcc gnd
mp_1 vcc a node_B vcc modp W=7200.00n L=350.00n
mp_2 node_B b OUT vcc modp W=3600.00n L=350.00n
mp_3 node_B c OUT vcc modp W=3600.00n L=350.00n
mn_1 OUT a gnd gnd modp W=1200.00n L=350.00n
mn_2 OUT b node_A gnd modp W=2400.00n L=350.00n
mn_3 node_A c gnd gnd modp W=2400.00n L=350.00n
