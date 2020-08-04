# armeria-example-for-jsug

The example app for [JSUG勉強会 2020年その6 LT大会！](https://jsug.doorkeeper.jp/events/109767)

see also: [JSUG勉強会で「Spring BootユーザのためのArmeria入門」というタイトルでLTしました](https://matsumana.info/blog/2020/07/30/introduce-to-armeria-for-spring-users/)

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
$ java -jar armeria-example-microservice2/build/libs/armeria-example-microservice2-*.jar
$ java -jar armeria-example-api-gateway/build/libs/armeria-example-api-gateway-*.jar
```

### How to call the APIs

```
# response from armeria-example-monolith
$ curl localhost:8083/hello/foo
Hello, foo

# response from armeria-example-microservice1
$ curl localhost:8083/konnichiwa/foo
こんにちは, foo

# response from armeria-example-microservice2
$ curl localhost:8083/hola/foo
Hola, foo
```
