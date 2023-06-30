# TODO LIST FOR TWOFOURTREE.JAVA
- ~~Finish basic search method~~
- ~~Create method for adding value to a node after it has found where it should go~~
- ~~create merge nodes method~~
- ~~create split four node method~~
- ~~create delete value method~~


# CURRENT TEST CASE OUTPUT
## for random test cases at least
```
CASE:      100 integers,       20 finds,       10 removals.  Generating...
  TwoFourTree add:       0ms  find:       1ms  del:       0ms  (        9 missing) find:      1ms  (Should be         9 missing)
  TreeSet     add:       1ms  find:       0ms  del:       0ms  (        9 missing) find:      0ms  (Should be         9 missing)
CASE:    1,000 integers,      200 finds,      100 removals.  Generating...
  TwoFourTree add:       1ms  find:       0ms  del:       1ms  (       91 missing) find:      0ms  (Should be        91 missing)
  TreeSet     add:       0ms  find:       0ms  del:       0ms  (       91 missing) find:      0ms  (Should be        91 missing)
CASE:   10,000 integers,    2,000 finds,    1,000 removals.  Generating...
  TwoFourTree add:       3ms  find:       0ms  del:       1ms  (      910 missing) find:      1ms  (Should be       910 missing)
  TreeSet     add:       3ms  find:       1ms  del:       1ms  (      910 missing) find:      1ms  (Should be       910 missing)
CASE:  100,000 integers,   20,000 finds,   10,000 removals.  Generating...
  TwoFourTree add:      32ms  find:       5ms  del:       7ms  (    9,063 missing) find:      5ms  (Should be     9,063 missing)
  TreeSet     add:      12ms  find:       3ms  del:       3ms  (    9,063 missing) find:      4ms  (Should be     9,063 missing)
CASE: 1,000,000 integers,  200,000 finds,  100,000 removals.  Generating...
  TwoFourTree add:     252ms  find:      30ms  Failed to delete 17903843
Failed to delete 279740416
Failed to delete 404877748
del:      47ms  (   90,607 missing) find:     33ms  (Should be    90,610 missing)
  TreeSet     add:     209ms  find:      31ms  del:      26ms  (   90,610 missing) find:     33ms  (Should be    90,610 missing)
CASE: 10,000,000 integers, 2,000,000 finds, 1,000,000 removals.  Generating...
  TwoFourTree add:   2,362ms  find:     352ms  Failed to delete 4825009
Failed to delete 10108542
Failed to delete 24401752
Failed to delete 28096969
Failed to delete 40178531
Failed to delete 109011983
Failed to delete 115425408
Failed to delete 129084699
Failed to delete 136212556
Failed to delete 150259784
Failed to delete 152816079
Failed to delete 187984413
Failed to delete 189511741
Failed to delete 194629753
Failed to delete 207199761
Failed to delete 208487766
Failed to delete 209917622
Failed to delete 238045766
Failed to delete 240159685
Failed to delete 241417071
Failed to delete 247286993
Failed to delete 264771794
Failed to delete 272318824
Failed to delete 311940266
Failed to delete 328471441
Failed to delete 352571854
Failed to delete 358290778
Failed to delete 428114304
Failed to delete 473501224
Failed to delete 474788909
Failed to delete 475170943
Failed to delete 483596413
del:     450ms  (  905,907 missing) find:    367ms  (Should be   905,936 missing)
  TreeSet     add:   2,604ms  find:     332ms  del:     214ms  (  905,936 missing) find:    353ms  (Should be   905,936 missing)
```
