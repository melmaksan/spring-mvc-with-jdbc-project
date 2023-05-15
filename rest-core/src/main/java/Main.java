import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.GiftCertificateService;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import com.epam.esm.service.abstraction.TagService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TAG_NAME = "birthday";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("coreApplicationContext.xml");

        TagService tagService = context.getBean("tagService", TagService.class);

        certificateRun(context, tagService);
        tagRun(tagService);
    }

    private static void certificateRun(ClassPathXmlApplicationContext context, TagService tagService) {
        GiftCertificateService certificateService =
                context.getBean("giftCertificateService", GiftCertificateService.class);
        GiftCertificateToTagService toTagService =
                context.getBean("giftCertificateToTagService", GiftCertificateToTagService.class);

        String certificateName = "rest_core";
        String secondTagName = "toppot";
        GiftCertificate certificate = newCertificate(certificateName, secondTagName);
        System.out.println("certificateRun method: " + "\n");

        System.out.println("All certificates: ");
        System.out.println(certificateService.getAllGiftCertificates() + "\n");

        System.out.println("Add giftCertificate + certificate by ID: ");
        long id = certificateService.addGiftCertificate(certificate);
        System.out.println(certificateService.getGiftCertificateById(id) + "\n");

        System.out.println("Get certificate by name: ");
        System.out.println(certificateService.getGiftCertificateByName(certificateName) + "\n");

        System.out.println("Get certificate by part: ");
        System.out.println(certificateService.getGiftCertificatesByPart(certificateName.substring(0, 2)) + "\n");

        System.out.println("Get certificate by tagName: ");
        Tag tag = tagService.getTagByName(TAG_NAME);
        System.out.println(getCertificatesByTag(tag.getId(), certificateService, toTagService) + "\n");

        System.out.println("All certificates asc date: ");
        System.out.println(certificateService.sortGiftCertificatesByDateAsc() + "\n");

        System.out.println("All certificates desc date: ");
        System.out.println(certificateService.sortGiftCertificatesByDateDesc() + "\n");

        System.out.println("All certificates asc name: ");
        System.out.println(certificateService.sortGiftCertificatesByNameAsc() + "\n");

        System.out.println("All certificates desc name: ");
        System.out.println(certificateService.sortGiftCertificatesByNameDesc() + "\n");

        System.out.println("Update giftCertificate: ");
        GiftCertificate certificateUpdate = certificateUpdate(id);
        long upd = certificateService.updateGiftCertificate(certificateUpdate);
        System.out.println(upd + ", " + certificateService.getGiftCertificateById(id) + "\n");

        System.out.println("Delete giftCertificate: ");
        long dlt = certificateService.deleteGiftCertificate(id);
        if (dlt > 0) {
            System.out.println("Certificate with id " + id + " was deleted" + "\n");
        } else {
            System.out.println("Certificate with id " + id + " wasn't deleted, please try again" + "\n");
        }
    }

    private static GiftCertificate certificateUpdate(long id) {
        GiftCertificate certificateUpdate = new GiftCertificate();
        List<Tag> tagList = new ArrayList<>();
        Tag tagUpdate = new Tag();
        tagUpdate.setName("hardkiss");
        tagList.add(tagUpdate);
        certificateUpdate.setId(id);
        certificateUpdate.setName(TAG_NAME);
        certificateUpdate.setTags(tagList);
        return certificateUpdate;
    }

    private static List<GiftCertificate> getCertificatesByTag
            (int id, GiftCertificateService certificateService, GiftCertificateToTagService toTagService) {
        List<GiftCertificate> certificates = new ArrayList<>();
        List<GiftCertificateToTag> certificateToTags = toTagService.findAllCertificatesByTagId(id);
        for (GiftCertificateToTag certificateToTag : certificateToTags) {
            certificates.add(certificateService.getGiftCertificateById(certificateToTag.getGiftCertificateId()));
        }
        return certificates;

    }

    private static GiftCertificate newCertificate(String name, String secondTagName) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName(name);
        certificate.setDescription(name.substring(5, 9));
        certificate.setPrice(1000);
        certificate.setDuration(99);
        Tag tag1 = newTag(TAG_NAME);
        Tag tag2 = newTag(secondTagName);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        certificate.setTags(tagList);
        return certificate;
    }

    private static Tag newTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }

    private static void tagRun(TagService tagService) {
        System.out.println("\n\n" + "tagRun method: " + "\n");

        System.out.println("All certificates: ");
        System.out.println(tagService.getAllTags() + "\n");

        System.out.println("Get certificate by name: ");
        Tag tag = tagService.getTagByName(TAG_NAME);
        System.out.println(tag);

        System.out.println("\n" + "Add tag + tag by ID: ");
        int id = tagService.addTag(newTag("patriot"));
        System.out.println(tagService.getTagById(id) + "\n");

        System.out.println("All certificates: ");
        System.out.println(tagService.getAllTags() + "\n");

        System.out.println("Delete tags: ");
        int dlt = tagService.deleteTag(id);
        if (dlt > 0) {
            tagService.deleteTag(tag.getId());
            System.out.println("Certificate with name patriot and " + tag.getName() + " were deleted" + "\n");
        } else {
            System.out.println("Certificate with name patriot and " + tag.getName() + " wasn't deleted, please try again" + "\n");
        }

    }
}
