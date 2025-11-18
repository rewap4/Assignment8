Real time results from running my code 

n buildKD KD(1000) BF(1000)
2 10 2.879417ms 1.543958ms 1.337792ms
2 100 833.667us 238.958us 972.625us
2 1000 4.562833ms 350.917us 2.240834ms
2 10000 18.805208ms 569.875us 21.015458ms
4 10 19.333us 205.542us 127.834us
4 100 83.209us 498.25us 476.25us
4 1000 1.292667ms 1.047750ms 4.022375ms
4 10000 19.551042ms 1.981041ms 35.337333ms
8 10 20.625us 231.167us 150.959us
8 100 81.875us 1.339459ms 646.958us
8 1000 1.164625ms 6.103083ms 5.210709ms
8 10000 18.276458ms 18.828167ms 51.94ms
16 10 25.5us 269.458us 212.417us
16 100 81.291us 1.611917ms 986.041us
16 1000 1.979625ms 18.222292ms 8.594500ms
16 10000 12.515750ms 214.067833ms 95.542834ms

For smaller values of n, brute force can keep up with KD in terms of the build cost.
As the n value grows, the KD algorithm is a better fit
As the k value grows, KD loses its advantage over the brute force algorithm

Hence it is better to use brute force for smaller datasets.

KD-tree is best for larger n values and lower k
Brute force is best for smaller n values and very high k