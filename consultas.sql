CREATE TABLE actividades (ts TEXT, type TEXT, source TEXT, destination TEXT, data TEXT);


SELECT * FROM actividades AS paso4 
   INNER JOIN actividades AS paso3
      ON paso3.data = paso4.data AND paso3.destination = paso4.source 
   INNER JOIN actividades AS paso2 
      ON paso2.data = paso4.data
   INNER JOIN actividades AS paso1
      ON paso1.source = paso2.source
WHERE 
   paso4.type = "copy" AND
   paso3.type = "copy" AND
   paso2.type = "conn" AND
   paso1.type = "scan" AND

   paso4.source LIKE "interna%" AND paso4.destination LIKE "externa%" AND
   paso3.source LIKE "interna%" AND paso3.destination LIKE "interna%" AND
   paso2.source LIKE "externa%" AND paso2.destination LIKE "interna%" AND
   paso1.source LIKE "externa%" AND paso1.destination LIKE "interna%";

ts|type|source|destination|data|ts|type|source|destination|data|ts|type|source|destination|data|ts|type|source|destination|data
2020-01-01 05:11|copy|interna_bbbb|externa_copy|1234|2020-01-01 04:07|copy|interna_aaaa|interna_bbbb|1234|2020-01-01 02:03|conn|externa_aaaa|interna_conn|1234|2020-01-01 00:51|scan|externa_aaaa|interna_scan|abcd

