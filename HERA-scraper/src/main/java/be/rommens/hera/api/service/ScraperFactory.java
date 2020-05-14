package be.rommens.hera.api.service;

import be.rommens.hera.api.Provider;
import be.rommens.hera.core.Scraper;

/**
 * User : cederik
 * Date : 14/05/2020
 * Time : 10:02
 */
public interface ScraperFactory {
    Scraper createScraper(Provider provider);
}
