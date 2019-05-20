# Keyword_Extraction

Finds keywords in text documents based on [TF-IDF scores](https://en.wikipedia.org/wiki/Tf%E2%80%93idf)


##### Running
Documents to use should be in /docs folders, 30 examples are currently there based on Wikipedia articles

1. Compile both DocumentFrequency and KeywordExractor (e.g. javac DocumentFrequency.java)
2. Run DocumentFrequency to obtain frequency.text
3. Run KeywordExractor to obtain tfidf_scores.txt, which ranks the top 5 keyword in each document
