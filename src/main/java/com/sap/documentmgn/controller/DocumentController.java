package com.sap.documentmgn.controller;

import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.service.DocumentService;
import jakarta.websocket.server.PathParam;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@Path("/api/documents")
public class DocumentController {
    DocumentService documentService = new DocumentService();

    @POST
    @Path("{docId}/versions/{verId}/approve")
    public Response DocumentApprove(@PathParam("docId") Long docId, @PathParam("verId") Long verId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Long userId = user.getId();

        documentService.approveVersion(docId, verId, userId);

        return Response.ok().build();
    }
}
