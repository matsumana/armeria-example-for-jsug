# armeria-example-for-jsug

The example app for [JSUG勉強会 2020年その6 LT大会！](https://jsug.doorkeeper.jp/events/109767)

# How to build

```
$ ./gradlew --no-daemon clean build
```

# How to start examples

## Example 1

![](https://s3-ap-northeast-1.amazonaws.com/static.matsumana.info/blog/armeria-example-for-jsug_example1.png)

### How to start the example apps

```
$ java -jar armeria-example-monolith/build/libs/armeria-example-monolith-*.jar
$ java -jar armeria-example-microservice1/build/libs/armeria-example-microservice1-*.jar
```

### How to call the APIs

```
$ curl localhost:8080/hello/foo
Hello, foo
$ curl localhost:8080/konnichiwa/foo
こんにちは, foo
```

## Example 2

![](https://s3-ap-northeast-1.amazonaws.com/static.matsumana.info/blog/armeria-example-for-jsug_example2.png)

### How to start the example apps

```
$ java -jar armeria-example-monolith/build/libs/armeria-example-monolith-*.jar
$ java -jar armeria-example-microservice1/build/libs/armeria-example-microservice1-*.jar
$ java -jar armeria-example-api-gateway/build/libs/armeria-example-api-gateway-*.jar
```

### How to call the APIs

```
$ curl localhost:8082/hello/foo
Hello, foo
$ curl localhost:8082/konnichiwa/foo
こんにちは, foo
```
