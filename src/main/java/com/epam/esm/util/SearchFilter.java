package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.SearchParams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
        filterByTagName(searchParams.getTagName());
        filterByPartOfName(searchParams.getName());
        filterByPartOfDescription(searchParams.getDescription());
        if(searchParams.isSortByDate() && searchParams.isSortByName()){
            sortByDateAndByName(searchParams.getSortType());
        }else if(searchParams.isSortByDate() || searchParams.isSortByName()){
            sortByDate(searchParams.getSortType());
            sortByName(searchParams.getSortType());
        }
        return certificates;
    }

    private void filterByTagName(String tagName) {
        if (Objects.nonNull(tagName)) {
            certificates = certificates.stream()
                    .filter(certificate -> certificate.getTags()
                            .stream()
                            .anyMatch(tag -> tag.getName().equals(tagName)))
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

}
