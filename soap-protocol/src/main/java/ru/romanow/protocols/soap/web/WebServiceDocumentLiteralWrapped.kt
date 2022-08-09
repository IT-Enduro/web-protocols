package ru.romanow.protocols.soap.web;

import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "WebServiceDocumentWrapped")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,
        use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface WebServiceDocumentLiteralWrapped {

    @WebMethod(operationName = "processRequest")
    @RequestWrapper(className = "RequestWrapper")
    @ResponseWrapper(className = "ResultWrapper")
    @WebResult(name = "TestObjectResponse")
    TestObjectResponse processRequest(
            @WebParam(name = "TestObjectRequest") TestObjectRequest request);
}
