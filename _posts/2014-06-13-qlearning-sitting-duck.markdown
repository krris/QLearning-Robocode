---
layout: post
title:  "Sitting duck results"
date:   2014-06-13 07:02:18
categories: jekyll update
---

Wyniki dla walki z robotem SittingDuck.
Ustawienia gry:

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

![Chart]( {{ site.images }}/sitting_duck_ql_13_06.png)
