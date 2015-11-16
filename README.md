# coevolution
Various models have been used to investigate the predator-prey system, initially as a pair of non-linear differential equations called Lotka-Volterra REF. These equations does not take any spatial parameters into account and treats only the population densities instead of discrete individuals. A method to introduce local interactions between adjacent predators and preys is agent based modeling. There are three possible outcomes of this kind of model, (i) the prey develops a stronger defense than the predator's offense and only the prey population survives, (ii) the predator develops a stronger offense than the prey’s defense and the predator population survives, (iii) both sides repeatedly co-evolve their levels of offense and defence and never gain a relative advantage (called Red Queen effect) REF. 

We want to do an agent-based model of a predator-prey system to investigate if co-evolution can arise in such a simple model. The evolving variable will be the speed of each agent, where faster predators are able to catch faster prey. A further addition to the model would be to introduce agent size, where the size is inversely proportional to speed and predators only are able to catch prey smaller than themselves. Will the speed (and size) of prey reflect that of the predators?