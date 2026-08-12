package com.jfc.rdb.postgres.repository.pcn;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.pcn.PcnAttachment;

@Repository
public interface PcnAttachmentRepository extends JpaRepository<PcnAttachment, UUID> {

    List<PcnAttachment> findByPcnIdOrderByUploadedAtAsc(UUID pcnId);

    void deleteByAttachmentIdAndPcnId(UUID attachmentId, UUID pcnId);
}
