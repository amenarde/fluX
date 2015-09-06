# fluX

Inspiration
Though epidemiologists use very effective outbreak-finding algorithms for many diseases, during flu season the flu spreads quickly and many cases go unreported or unconfirmed, meaning that they don't make it to these algorithms. This inspired us to make a program that can identify outbreaks in communities across the United States more quickly and effectively.

What it does
fluX uses the user's location, age, and the time of year to evaluate their risk of catching the flu. fluX then uses information about their flu history and this risk evaluation in reference with all our other users to look for outbreaks throughout the United States.

How we built it
We built fluX in eclipse, breaking it into: interfaces, backend data analysis (CDC-sourced data and user-sourced data), and risk/outbreak analysis.

What's next for fluX
fluX only exists offline right now; our first step will be getting fluX online so that it can source data from users all over the country and hence make more accurate predictions. Next, we will implement machine learning with even bigger CDC datasets than we are using now to make more accurate predictions of risk and evaluations of outbreaks. As fluX continues to grow, we will increase the measured parameters to generate an even more immersive and specific analysis of flu spread in the country. Finally, we will connect with worldwide flu data to bring fluX all over the world.
