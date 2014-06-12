# QLearning Robocode
Robocode robot which uses Reinforcement Learning algorithm (QLearning).

### How to run this robot
1. Download and install [Robocode](http://robocode.sourceforge.net/) (at least version 1.9.2.1)
2. Download and install [Gradle](http://www.gradle.org/)
3. Clone this repository.
```git clone https://github.com/krris/QLearning-Robocode.git```
4. Go to download directory and run: <br/>
```gradle build``` <br/>
```gralde copyDeps```
5. Modify robocodePath in <i>config.sh</i>. It should point your Robocode installation.
6. Modify in <i>src/main/resources/application.conf</i>:
    - <i>rewardsPath</i> (place where 'rewards' file generated by Robocode will be saved)
    - <i>chartPath</i> (place where chart with rewards/round will be printed)
    - <i>battleConfigPath</i> (change the path (according to your home directory) it points to <i>battles/generate.battle</i>)
7. ```cd scripts```
8. ``` ./run.sh ```

### FAQ
> Where I can set QLearning parameters, number of rounds, robots etc?

You can change every configuration in <b>application.conf</b>
