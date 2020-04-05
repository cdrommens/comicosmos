package be.rommens.hera.core;

import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;

import java.io.IOException;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:54
 */
public interface Scraper {

    ScrapedComic scrapeComic(String technicalComicName) throws IOException;

    ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException;
}
