# Crawler

Crawler's main class accepts two command line arguments
1. Base Url of website to crawl eg. https://ipinfo.io
2. First page to start crawling from eg. https://ipinfo.io/countries
3. Output file path

Once program is completed, we will have json file in outputFilePath with ASN data

After 50 hits with ASNs of a country  eg.  "https://ipinfo.io/countries/US/" , IPinfo website server throws error status code "429 : Too Many Request" . Its the Site security constraint.
We have to wait for some time (around 3 hours) to run the program again
