---
layout: post
title:  "Reprezentacja stanu robota"
date:   2014-06-13 07:00:18
categories: jekyll update
---

Stan reprezentowany jest przez:

* Odległość do najbliższego przeciwnika
* Kąt do najbliższego przeciwnika

Przykładowe zdefiniowanie stanów:
{% highlight java %}
// Possible distances to enemy
public static final Range[] DISTANCES_TO_ENEMY = {
        new Range(0, 50, RangeType.DISTANCES_TO_ENEMY),
        new Range(50, 100, RangeType.DISTANCES_TO_ENEMY),
        new Range(100, 200, RangeType.DISTANCES_TO_ENEMY),
        new Range(200, 300, RangeType.DISTANCES_TO_ENEMY),
        new Range(300, 400, RangeType.DISTANCES_TO_ENEMY),
        new Range(400, 500, RangeType.DISTANCES_TO_ENEMY),
        new Range(500, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
};

// Possible angles to enemy
public static final Range[] ANGLES_TO_ENEMY = {
        new Range(-180, -135, RangeType.ANGLES_TO_ENEMY),
        new Range(-135, -90, RangeType.ANGLES_TO_ENEMY),
        new Range(-90, -45, RangeType.ANGLES_TO_ENEMY),
        new Range(-45, 0, RangeType.ANGLES_TO_ENEMY),
        new Range(0, 45, RangeType.ANGLES_TO_ENEMY),
        new Range(45, 90, RangeType.ANGLES_TO_ENEMY),
        new Range(90, 135, RangeType.ANGLES_TO_ENEMY),
        new Range(135, 180, RangeType.ANGLES_TO_ENEMY),
};
{% endhighlight %}