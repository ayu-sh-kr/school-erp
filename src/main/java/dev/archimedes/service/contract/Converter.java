package dev.archimedes.service.contract;

public interface Converter<P, S> {
    S convert(P p, S s);
    P reverseConvert(S s, P p);
}
