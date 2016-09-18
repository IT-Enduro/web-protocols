package ru.romanow.protocols.web;

import ru.romanow.protocols.model.TestObjectRequest;
import ru.romanow.protocols.model.TestObjectResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by ronin on 16.09.16
 */
@WebService(name = "WebServiceRpcLiteral")
@SOAPBinding(style = SOAPBinding.Style.RPC,
        use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WebServiceRpcLiteral {

    @WebMethod(operationName = "processRequest")
    @WebResult(name = "TestObjectResponse")
    TestObjectResponse processRequest(
            @WebParam(name = "TestObjectRequest") TestObjectRequest request);
}