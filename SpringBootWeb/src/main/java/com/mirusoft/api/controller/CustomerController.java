package com.mirusoft.api.controller;

import com.mirusoft.common.core.MirusoftController;
import com.mirusoft.common.core.api.HttpSuccessCode;
import com.mirusoft.common.core.api.RestApiResponse;
import com.mirusoft.common.core.exception.MirusoftDataNotFoundException;
import com.mirusoft.common.core.util.string.StringHelper;
import com.mirusoft.business.model.Customer;
import com.mirusoft.business.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@Tag(name = "Customers API Controller", description = "고객관리를 위한 메인 컨트롤러" )
@RestController
@RequestMapping("/v1/customers")
public class CustomerController extends MirusoftController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService){
        this.customerService = customerService;
    }

    /*@GetMapping("/users")
    public ResponseEntity<?> findAll() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<Customer> customerList = customerService.findAll();
        if (!customerList.isEmpty()) {
            map.put("status", 1);
            map.put("data", customerList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not foud");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }*/

    @Operation(operationId = "findAll", summary = "전체고객조회", description = "전체 고객정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RestApiResponse.class)))
    })
    @GetMapping("/users")
    public ResponseEntity<RestApiResponse<Object>> findAll(){

        List<Customer> customers = customerService.findAll();

        if(customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(customers)
                .resultCode(HttpSuccessCode.SELECT_SUCCESS.getStatus())
                .resultMessage(HttpSuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@GetMapping("/user/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Customer customer;
        try {
            customer = customerService.findById(id);
            if (customer == null) {
                map.clear();
                map.put("status", 0);
                map.put("message", "Data is not fount");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Failed to Such");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("status", 1);
        map.put("data", customer);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }*/

    @Operation(operationId = "findById", summary = "고객상세조회", description = "고객의 상세정보를 조회한다")
    @GetMapping("/user/{id}")
    public ResponseEntity<RestApiResponse<Object>> findById(@PathVariable long id){

        Customer customer = customerService.findById(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(customer)
                .resultCode(HttpSuccessCode.SELECT_SUCCESS.getStatus())
                .resultMessage(HttpSuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*@PostMapping("/save")
    public ResponseEntity<?> insert(@RequestBody Customer customer) {
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        try {
            customer.setName(StringHelper.removeWhitespaces(customer.getName()));
            customerService.insert(customer);
        } catch (DuplicateKeyException e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Failed to save Data : Duplicated(id:" + customer.getId()+")");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Failed to save Data");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("status", 1);
        map.put("message", "Data Saved Successfully");
        map.put("data", customerService.findById(customer.getId()));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }*/

    @Operation(operationId = "insert", summary = "고객등록", description = "고객을 등록한다")
    @PostMapping("/save")
    public ResponseEntity<EntityModel<RestApiResponse<Object>>> insert(@Valid @RequestBody Customer reqCustomer){

        reqCustomer.setName(StringHelper.removeWhitespaces(reqCustomer.getName()));
        customerService.insert(reqCustomer);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(reqCustomer)
                .resultCode(HttpSuccessCode.INSERT_SUCCESS.getStatus())
                .resultMessage(HttpSuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        Link link = Link.of("/v1/customers/" + reqCustomer.getId());

        EntityModel<RestApiResponse<Object>> entityModel = EntityModel.of(response, link);

        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Customer customerDetail) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Customer customer = customerService.findById(id);
            if (customer != null) {
                customerService.update(customerDetail);
            } else {
                map.clear();
                map.put("status", 0);
                map.put("message", "Failed to Update Data : Data is not Found");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Failed to Update Data");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("status", 1);
        map.put("message", "Data Updated Successfully");
        map.put("data", customerService.findById(id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }*/

    @Operation(operationId = "updateById", summary = "고객정보수정", description = "고객의 정보를 수정한다")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RestApiResponse<Object>>> updateById(@Valid @RequestBody Customer customer) throws Exception{

        Customer existCustomer = customerService.findById(customer.getId());

        if(existCustomer == null){
            throw new MirusoftDataNotFoundException("Failed to Update Data : Data is not Found");
        }

        customerService.update(customer);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(customer)
                .resultCode(HttpSuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMessage(HttpSuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        Link link = Link.of("/v1/customers/" + customer.getId());

        EntityModel<RestApiResponse<Object>> entityModel = EntityModel.of(response, link);

        return ResponseEntity.status(HttpStatus.OK).body(entityModel);

    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Customer customer = customerService.findById(id);
            if (customer != null) {
                customerService.delete(customer.getId());
            } else {
                map.clear();
                map.put("status", 0);
                map.put("message", "Failed to Deleted Data : Data is not Found");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Failed to Deleted Data");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("status", 1);
        map.put("message", "Data Delete Successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }*/

    @Operation(operationId = "delete", summary = "고객삭제", description = "고객을 삭제한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<Object>> delete(@PathVariable("id") long id) throws Exception {

        Customer existCustomer = customerService.findById(id);

        if(existCustomer == null){
            throw new MirusoftDataNotFoundException("Failed to Delete Data : Data is not Found");
        }

        customerService.delete(id);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(id)
                .resultCode(HttpSuccessCode.DELETE_SUCCESS.getStatus())
                .resultMessage(HttpSuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
