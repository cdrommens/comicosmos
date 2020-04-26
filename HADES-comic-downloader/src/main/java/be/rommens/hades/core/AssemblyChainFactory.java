package be.rommens.hades.core;

/**
 * User : cederik
 * Date : 23/04/2020
 * Time : 15:42
 */
public interface AssemblyChainFactory<I> {

    Command createAssemblyChain(I input);
}
