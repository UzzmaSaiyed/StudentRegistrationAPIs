package com.student.java;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject; 

@Path("/students")
public class StudentService {
    private static final Map<String, JSONObject> studentDB = new ConcurrentHashMap<>();

    @POST
    @Path("/registerStudent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerStudent(@Context HttpServletRequest request, String studentData) {
        JSONObject responseJSON = new JSONObject();

        if (studentData == null || studentData.trim().isEmpty()) {
            return badRequest("Invalid JSON input");
        }

        try {
            JSONObject inputJSON = new JSONObject(studentData);
            String id = inputJSON.optString("id", "").trim();
            String name = inputJSON.optString("name", "").trim();
            String age = inputJSON.optString("age", "").trim();
            String email = inputJSON.optString("email", "").trim();
            String rollno = inputJSON.optString("rollno", "").trim();
            String course = inputJSON.optString("course", "").trim();

            if (id.isEmpty() || name.isEmpty() || age.isEmpty() || email.isEmpty() || rollno.isEmpty() || course.isEmpty()) {
                return badRequest("All fields are required");
            }

            JSONObject student = new JSONObject();
            student.put("id", id);
            student.put("name", name);
            student.put("age", age);
            student.put("email", email);
            student.put("rollno", rollno);
            student.put("course", course);

            // Check and insert atomically
            JSONObject existingStudent = studentDB.putIfAbsent(id, student);
            if (existingStudent != null) {
                return badRequest("Student ID already exists");
            }

            responseJSON.put("status", 201);
            responseJSON.put("message", "Student registered successfully");
            return Response.status(Response.Status.CREATED).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON).build();

        } catch (JSONException e) {
            return badRequest("Invalid JSON format");
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GET
    @Path("/getStudent/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") String id) {
        if (id == null || id.trim().isEmpty()) {
            return badRequest("Student ID cannot be empty");
        }

        JSONObject student = studentDB.get(id);
        if (student == null) {
            return notFound("Student Not Found");
        }

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", 200);
        responseJSON.put("student", student);
        return Response.status(Response.Status.OK).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    // Utility Methods
    private Response badRequest(String message) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", 400);
        responseJSON.put("message", message);
        return Response.status(Response.Status.BAD_REQUEST).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    private Response notFound(String message) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", 404);
        responseJSON.put("message", message);
        return Response.status(Response.Status.NOT_FOUND).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    private Response serverError(Exception e) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", 500);
        responseJSON.put("error", e.getClass().getSimpleName());
        responseJSON.put("message", e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}