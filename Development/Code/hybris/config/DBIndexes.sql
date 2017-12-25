DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'HOTFIX_171_HYBRIS_ENUMVALUES';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.HOTFIX_171_HYBRIS_ENUMVALUES on HYBRIS.ENUMERATIONVALUES("CODE","TYPEPKSTRING")';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'HOTFIX_171_CAT2PRODREL';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.HOTFIX_171_CAT2PRODREL on HYBRIS.CAT2PRODREL("SOURCEPK","TYPEPKSTRING")';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'CSQSCORE_IDX01';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'CREATE INDEX HYBRIS.CSQSCORE_IDX01 ON CSQSCORE (P_CODE, P_CATALOGVERSION, TYPEPKSTRING) tablespace HYBRISIDX pctfree 10 initrans 11 maxtrans 255 storage(initial 1M next 1M minextents 1 maxextents unlimited)';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'AUDITHISTORYVALUE_IDX01';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'CREATE INDEX HYBRIS.AUDITHISTORYVALUE_IDX01 ON AUDITHISTORYVALUE (P_CODE, TYPEPKSTRING) tablespace HYBRISIDX pctfree 10 initrans 11 maxtrans 255 storage(initial 1M next 1M minextents 1 maxextents unlimited)';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'CMSCOMPONENT_IDX01';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'CREATE INDEX HYBRIS.CMSCOMPONENT_IDX01 ON CMSCOMPONENT (TYPEPKSTRING, P_KEY, P_CATALOGVERSION) tablespace HYBRISIDX pctfree 10 initrans 11 maxtrans 255 storage(initial 1M next 1M minextents 1 maxextents unlimited)';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_CAT2CATREL';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_CAT2CATREL on HYBRIS.CAT2CATREL (TYPEPKSTRING, TARGETPK, SOURCEPK) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_CAT2PRODREL';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_CAT2PRODREL on HYBRIS.CAT2PRODREL (TYPEPKSTRING, TARGETPK, SOURCEPK) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_ENUMERATIONVALUES';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_ENUMERATIONVALUES on HYBRIS.ENUMERATIONVALUES (PK, CODE) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_CAT2CATREL_2';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_CAT2CATREL_2 on HYBRIS.CAT2CATREL (TYPEPKSTRING, SOURCEPK, TARGETPK) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_MEDIAS';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_MEDIAS on HYBRIS.MEDIAS (TYPEPKSTRING, P_CONTEXTITEM) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_CATALOGVERSIONS';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_CATALOGVERSIONS on HYBRIS.CATALOGVERSIONS (PK, TYPEPKSTRING) tablespace HYBRISIDX';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_MEDIAS_2';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_MEDIAS_2 on HYBRIS.MEDIAS (OWNERPKSTRING, TYPEPKSTRING) tablespace HYBRISIDX';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_ABSTRACTTEMPORALITEM';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_ABSTRACTTEMPORALITEM on HYBRIS.ABSTRACTTEMPORALITEM (TYPEPKSTRING, P_CATALOGVERSION) tablespace HYBRISIDX';
   END IF;
END;
/


DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_ACCOMMODATION';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_ACCOMMODATION on HYBRIS.ACCOMMODATION (TYPEPKSTRING, P_CATALOGVERSION) tablespace HYBRISIDX';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_ACCOMMODATION_2';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'create index HYBRIS.IX_Temp_ACCOMMODATION_2 on HYBRIS.ACCOMMODATION (TYPEPKSTRING, P_ONLINEDATE) tablespace HYBRISIDX';
   END IF;
END;
/

DECLARE
   i   INTEGER;
BEGIN
   SELECT COUNT (*)
     INTO i
     FROM user_indexes
    WHERE index_name = 'IX_Temp_CMSCOMPONENT';

   IF i = 0
   THEN
      EXECUTE IMMEDIATE
         'Create index HYBRIS.IX_Temp_CMSCOMPONENT on HYBRIS.CMSCOMPONENT ("P_KEY", "TYPEPKSTRING", "P_CATALOGVERSION", "PK") tablespace HYBRISIDX';
   END IF;
END;
/
