package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Number;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.RemoteGeneratorServiceGrpc;

public class RemoteGeneratorServiceImpl extends RemoteGeneratorServiceGrpc.RemoteGeneratorServiceImplBase {
    private final GeneratorService generatorService;

    public RemoteGeneratorServiceImpl(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Override
    public void generate(NumberRequest numberRequest, StreamObserver<Number> responseObserver) {
        generatorService.setValue(numberRequest.getFirstValue());
        var value = numberRequest.getFirstValue();
        while(value <= numberRequest.getLastValue()){
            value = generatorService.generate();
            responseObserver.onNext(Number.newBuilder().setNumber(value).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}
