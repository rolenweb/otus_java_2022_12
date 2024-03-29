package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Number;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.RemoteGeneratorServiceGrpc;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var serverNumber = new AtomicInteger();
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var newStub = RemoteGeneratorServiceGrpc.newStub(channel);
        newStub.generate(NumberRequest.newBuilder().setFirstValue(0).setLastValue(30).build(), new StreamObserver<Number>() {
            @Override
            public void onNext(Number value) {
                serverNumber.getAndSet(value.getNumber());
                System.out.printf("New value from server %s%n", value.getNumber());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Request completed");
            }
        });
        var clientNumber = 0;
        for (int i = 0; i < 50; i++) {
            clientNumber = clientNumber + serverNumber.getAndSet(0) + 1;

            System.out.printf("Client value %s%n", clientNumber);
            Thread.sleep(1000);
        }

        channel.shutdown();
    }
}
