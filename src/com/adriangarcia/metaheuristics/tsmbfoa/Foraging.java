package com.adriangarcia.metaheuristics.tsmbfoa;

/**
 *
 * @author Adrian
 */
public interface Foraging {
    public abstract void chemotaxis(Configurator configurator, Problem problem, Function function);
    public abstract void grouping();
    public abstract void reproduction(Configurator configurator);
    public abstract void eliminationDispersal(Configurator configurator, Problem problem, Function function);
    public abstract void updateStepSize(Configurator configurator, Problem problem);
}
