package com.gotrecha.servlet;

import com.gotrecha.util.traits.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by dustin on 4/26/14.
 */
public abstract class BaseServlet extends HttpServlet implements UtilTrait{


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}

	protected void doProcess(HttpServletRequest request,HttpServletResponse response){
		//TODO - Add some kind of security here
		process(request,response);
	}

	protected abstract void process(HttpServletRequest request,HttpServletResponse response);
}
