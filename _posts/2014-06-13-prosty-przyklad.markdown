---
layout: post
title:  "Prosty przykład standardowego algorytmu Q-learning"
date:   2014-06-13 07:02:18
categories: jekyll update
---

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

###Wyniki po 200 rundach:

|Robot Name|Total Score|Survival|Surv Bonus|Bullet Dmg|Bullet Bonus|Ram Dmg * 2|Ram Bonus|1sts|2nds|3rds|
|-|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|  
|1st: LearningRobot*   |51782 (96%) |8100    |1620    |0   |0   |37219   |4843    |165 |35  |0   |
|2nd: sample.SittingDuck |2100 (4%)   |1750    |350 |0   |0   |0   |0   |38  |162 |0   |

![Chart]( {{ site.images }}/sitting_duck_ql_13_06.png)
