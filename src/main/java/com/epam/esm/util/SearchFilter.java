package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.SearchParams;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exeptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class SearchFilter {

    private List<GiftCertificateDTO> certificates;
    private SearchParams searchParams;

    public List<GiftCertificateDTO> doFilter() {
        filterByTagNames(searchParams.getTagName());
        filterByPartOfName(searchParams.getName());
        filterByPartOfDescription(searchParams.getDescription());
        if(searchParams.isSortByDate() && searchParams.isSortByName()){
            sortByDateAndByName(searchParams.getSortType());
        }else if(searchParams.isSortByDate() || searchParams.isSortByName()){
            sortByDate(searchParams.getSortType());
            sortByName(searchParams.getSortType());
        }
        doPagination(searchParams.getPage());
        return certificates;
    }

    private void filterByTagNames(List<TagDTO> tagNames) {
        if (Objects.nonNull(tagNames) && !tagNames.isEmpty()) {
            certificates = certificates.stream()
                    .filter(giftCertificate -> tagNames.stream()
                            .allMatch(tagDTO -> giftCertificate.getTags().stream()
                                    .anyMatch(tag -> tag.getName().equals(tagDTO.getName()))))
                    .collect(Collectors.toList());
        }
    }

    private void filterByPartOfName(String partOfName) {
        if (Objects.nonNull(partOfName)) {
            certificates = certificates.stream()
                    .filter(certificate -> certificate.getName().contains(partOfName))
                    .collect(Collectors.toList());
        }
    }

    private void filterByPartOfDescription(String partOfDescription) {
        if (Objects.nonNull(partOfDescription)) {
            certificates = certificates.stream()
                    .filter(certificate -> certificate.getDescription().contains(partOfDescription))
                    .collect(Collectors.toList());
        }
    }

    private void sortByDate(String sortType) {
        if (Objects.nonNull(sortType) && searchParams.isSortByDate()) {
            if (sortType.equals("ASC")) {
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getLastUpdateDate, Comparator.naturalOrder()))
                        .collect(Collectors.toList());
            } else if (sortType.equals("DESC")) {
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getLastUpdateDate, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }
    }

    private void sortByName(String sortType){
        if (Objects.nonNull(sortType) && searchParams.isSortByName()) {
            if (sortType.equals("ASC")) {
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getName, Comparator.naturalOrder()))
                        .collect(Collectors.toList());
            } else if (sortType.equals("DESC")) {
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getName, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }
    }

    private void sortByDateAndByName(String sortType){
        if(Objects.nonNull(sortType)){
            if (sortType.equals("ASC")){
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getName, Comparator.naturalOrder())
                                .thenComparing(GiftCertificateDTO::getLastUpdateDate, Comparator.naturalOrder()))
                        .collect(Collectors.toList());
            } else if (sortType.equals("DESC")) {
                certificates = certificates.stream()
                        .sorted(Comparator.comparing(GiftCertificateDTO::getName, Comparator.reverseOrder())
                                .thenComparing(GiftCertificateDTO::getLastUpdateDate, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }
    }

    private void doPagination(Integer page){
        int pageSize = 10;
        if(Objects.nonNull(page)){
            if (page <= 0 || page > Math.ceil((double) certificates.size() /10)) {
                throw new BadRequestException("page " + page + " are not available");
            }
            certificates = certificates.stream()
                    .skip((long) (page - 1) *pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }
    }

}
