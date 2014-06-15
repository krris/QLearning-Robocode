---
layout: post
title:  "Prosty przykład Approximate Q-learning"
date:   2014-06-13 09:22:00
categories: jekyll update
---

Tym razem zastosowany jest algorytm Approximation Q-learning.


    Q(s,a) = weight_1 * feature_1(s,a) + ... + weight_n * feature_n(s,a)
    difference = [r + gamma * max Q(s',a')] - Q(s,a)
    Q(s,a) <- Q(s,a) + alpha * difference
    w_i <- w_i + alpha  * difference * feature_i(s,a)


Kod algorytmu można zobaczyć tutaj: [ApproximateQLearning.java](https://github.com/krris/QLearning-Robocode/blob/master/src/main/java/io/github/krris/qlearning/ApproximateQLearning.java)

Wyniki dla prostej walki z robotem SittingDuck (robot stojący w miejscu). Celem jest aby robot nauczył się namierzać i taranować przeciwnika.

###Ustawienia gry:
{% highlight yaml %}
alpha:      0.2
gamma:      0.8
epsilon:    0.05

# Rewards
hitAWall:           -10
collisionWithEnemy: 5
livingReward:       -1

learningRounds:      200
optimalPolicyRounds: 0

battlefieldWidth:   400
battlefieldHeight:  400
{% endhighlight %}

###Wyniki po 200 rundach (uczenie się):

|Robot Name|Total Score|Survival|Surv Bonus|Bullet Dmg|Bullet Bonus|Ram Dmg * 2|Ram Bonus|1sts|2nds|3rds|
|-|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|  
|1st: LearningRobot*   |40844 (83%) |2800    |560 |0   |0   |35837   |1648    |56  |144 |0   |
|2nd: SittingDuck |8640 (17%)  |7200    |1440    |0   |0   |0   |0   |144 |56  |0   |

![Chart]( {{ site.images }}/sitting_duck_ql_approximate_15_06.png)

###Wyniki po 50 rundach (optymalna strategia)

|Robot Name|Total Score|Survival|Surv Bonus|Bullet Dmg|Bullet Bonus|Ram Dmg * 2|Ram Bonus|1sts|2nds|3rds|
|-|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|  
|1st: LearningRobot*   |14403 (100%)    |2450    |490 |0   |0   |9994    |1469    |49  |1   |0   |
|2nd: SittingDuck |60 (0%) |50  |10  |0   |0   |0   |0   |1   |49  |0   |

![Chart]( {{ site.images }}/sitting_duck_ql_approximate_optimal_15_06.png)
