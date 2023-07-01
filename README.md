# TODO LIST FOR TWOFOURTREE.JAVA
- ~~Finish basic search method~~
- ~~Create method for adding value to a node after it has found where it should go~~
- ~~create merge nodes method~~
- ~~create split four node method~~
- ~~create delete value method~~


# CURRENT TEST CASE OUTPUT
## for random test cases at least
```
Static test: first few prime numbers:
      2
    3
      5
  7
      11
    13
      17
19
      23
    29
      31
  37
      41
    43
      47
  53
      59
    67
      71
    73
      79
    83
      89
      97

Without 37:
      2
    3
      5
  7
      11
    13
      17
19
      23
    29
      31
  41
      43
      47
    53
      59
  67
      71
    73
      79
    83
      89
      97

Without 73:
      2
    3
      5
  7
      11
    13
      17
19
      23
    29
      31
  41
      43
      47
    53
      59
  67
      71
    79
      83
    89
      97

Without 97:
      2
    3
      5
  7
      11
    13
      17
19
      23
    29
      31
  41
      43
      47
    53
      59
  67
      71
    79
      83
      89
CASE:      100 integers,       20 finds,       10 removals.  Generating...
  TwoFourTree add:       1ms  find:       0ms  del:       1ms  (        9 missing) find:      1ms  (Should be         9 missing)
  TreeSet     add:       1ms  find:       0ms  del:       0ms  (        9 missing) find:      0ms  (Should be         9 missing)
CASE:    1,000 integers,      200 finds,      100 removals.  Generating...
  TwoFourTree add:       1ms  find:       0ms  del:       0ms  (       91 missing) find:      1ms  (Should be        91 missing)
  TreeSet     add:       1ms  find:       0ms  del:       0ms  (       91 missing) find:      0ms  (Should be        91 missing)
CASE:   10,000 integers,    2,000 finds,    1,000 removals.  Generating...
  TwoFourTree add:       3ms  find:       1ms  del:       2ms  (      910 missing) find:      1ms  (Should be       910 missing)
  TreeSet     add:       3ms  find:       0ms  del:       1ms  (      910 missing) find:      0ms  (Should be       910 missing)
CASE:  100,000 integers,   20,000 finds,   10,000 removals.  Generating...
  TwoFourTree add:      31ms  find:       4ms  del:       5ms  (    9,063 missing) find:      5ms  (Should be     9,063 missing)
  TreeSet     add:      12ms  find:       3ms  del:       2ms  (    9,063 missing) find:      3ms  (Should be     9,063 missing)
CASE: 1,000,000 integers,  200,000 finds,  100,000 removals.  Generating...
  TwoFourTree add:     246ms  find:      30ms  del:      41ms  (   90,610 missing) find:     30ms  (Should be    90,610 missing)
  TreeSet     add:     208ms  find:      30ms  del:      26ms  (   90,610 missing) find:     32ms  (Should be    90,610 missing)
CASE: 10,000,000 integers, 2,000,000 finds, 1,000,000 removals.  Generating...
  TwoFourTree add:   2,213ms  find:     348ms  del:     414ms  (  905,936 missing) find:    337ms  (Should be   905,936 missing)
  TreeSet     add:   2,498ms  find:     438ms  del:     231ms  (  905,936 missing) find:    401ms  (Should be   905,936 missing)
```
