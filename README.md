# exception
스프링부트 exception 적용
Exception처리
웹 어플리케이션의 입장에서 바라 보았을때 , 에러가 났을때 내려줄수 있는 방법은 많지 않다.
1.에러페이지
2.4XXX , 5XXX ERROR
3.Client가 200외에 처리를 하지 못할때는 200을 내려주고 별도의 에러 메세지 전달.

@Exception처리
1.@ControllerAdvice : Global예외 처리 및 특정 package/Controller 예외 처리
2.@ExceptionHandler : 특정 Controller의 예외처리
