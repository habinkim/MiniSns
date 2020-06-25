package com.bpns.mobile.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bpns.mobile.mini.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
