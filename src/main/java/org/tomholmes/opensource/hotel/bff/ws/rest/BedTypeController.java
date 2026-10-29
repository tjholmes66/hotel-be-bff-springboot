package org.tomholmes.opensource.hotel.bff.ws.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.service.BedTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bedType")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "oauth2")
public class BedTypeController
{
    private final BedTypeService bedTypeService;

    public BedTypeController(BedTypeService bedTypeService) {
        this.bedTypeService = bedTypeService;
    }

    @GetMapping
    public ResponseEntity<List<BedTypeRecord>> getAllBedTypes()
    {
        List<BedTypeRecord> bedTypeRecordList = bedTypeService.getAllBedTypeRecords();
        return ResponseEntity.ok(bedTypeRecordList);
    }

    @GetMapping("/{bedTypeId}")
    public ResponseEntity<BedTypeRecord> getBedTypeById(@PathVariable Long bedTypeId)
    {
        BedTypeRecord bedTypeRecord = bedTypeService.getBedTypeRecordById(bedTypeId);
        return ResponseEntity.ok(bedTypeRecord);
    }

    @PostMapping
    public ResponseEntity<BedTypeRecord> createNewBedType(@RequestBody BedTypeRecord bedTypeRecord)
    {
        BedTypeRecord savedRecord = bedTypeService.createBedType(bedTypeRecord);
        return ResponseEntity.ok(savedRecord);
    }

}
