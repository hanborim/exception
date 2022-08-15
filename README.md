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


'-22.08.15 update
Part3-스프링 입문-ch06.-04.spring boot를 validation을 통한 모범사례(2) 
ApiControllerAdvice 아래 로직 추가 
1.MethodArgumentNotValidException
2.ConstraintViolationException
  //아큐먼트 잘 넣었는데 , 그아큐먼트의 정보 가 잘못 되면 타는 로직
         //http://localhost:8080/api/user?name&age=0 , age가 0일때
         //어떠한 필드가 잘못됬는지 정보를 가지고 있음
3.MissingServletRequestParameterException
//http://localhost:8080/api/user?name&age 아무값도 없을때 타는로직
에러 리턴값 클래스 생성해서 이쁘게 찍어주기

{
    "statusCode": "400 BAD_REQUEST",
    "requestUrl": "/api/user",
    "code": null,
    "message": "",
    "resultCode": "FAIL",
    "errorList": [
        {
            "field": "name",
            "message": "크기가 2에서 2147483647 사이여야 합니다",
            "invlid": ""
        }
    ]
}
