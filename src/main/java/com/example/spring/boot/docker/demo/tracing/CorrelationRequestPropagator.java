package com.example.spring.boot.docker.demo.tracing;

import brave.internal.codec.HexCodec;
import brave.internal.propagation.StringPropagationAdapter;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;
import brave.propagation.TraceContextOrSamplingFlags;
import java.util.Arrays;
import java.util.List;

class CorrelationRequestPropagator extends Propagation.Factory implements Propagation<String> {

    private static final String TRACE_ID_HEADER_NAME = "X-Correlation-Id";
    private static final String SPAN_ID_HEADER_NAME = "X-Request-Id";

    @Override
    public List<String> keys() {
        return Arrays.asList(TRACE_ID_HEADER_NAME, SPAN_ID_HEADER_NAME);
    }

    @Override
    public <R> Injector<R> injector(final Setter<R, String> setter) {
        return (traceContext, request) -> {
            setter.put(request, TRACE_ID_HEADER_NAME, traceContext.traceIdString());
            setter.put(request, SPAN_ID_HEADER_NAME, traceContext.spanIdString());
        };
    }

    @Override
    public <R> Extractor<R> extractor(final Getter<R, String> getter) {
        //@formatter:off
        return request -> TraceContextOrSamplingFlags.create(TraceContext.newBuilder()
                    .traceId(HexCodec.lowerHexToUnsignedLong(getter.get(request, TRACE_ID_HEADER_NAME)))
                    .spanId(HexCodec.lowerHexToUnsignedLong(getter.get(request, SPAN_ID_HEADER_NAME)))
                .build());
        //@formatter:on
    }

    @Override
    public <K> Propagation<K> create(final KeyFactory<K> keyFactory) {
        return StringPropagationAdapter.create(this, keyFactory);
    }

}
