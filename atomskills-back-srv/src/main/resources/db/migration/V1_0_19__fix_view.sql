DROP VIEW division_prizes;
DROP VIEW organization_prizes;


CREATE
OR REPLACE VIEW division_prizes AS (
SELECT division,
       COUNT(CASE WHEN rank = 1 THEN 1 END) AS first_places,
       COUNT(CASE WHEN rank = 2 THEN 1 END) AS second_places,
       COUNT(CASE WHEN rank = 3 THEN 1 END) AS third_places
FROM (
    SELECT d.name AS division,
           cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) AS score,
           ROW_NUMBER() OVER (PARTITION BY d.id ORDER BY cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) DESC) AS rank
    FROM rsatom.competence c
    JOIN rsatom.employee e ON c.user_name = e.full_name
    JOIN rsatom.workspace_correction wc ON wc.name = e.workplace
    JOIN rsatom.division d ON d.id = wc.division_id
) AS subquery
WHERE rank <= 3
GROUP BY division
);

CREATE
OR REPLACE VIEW organization_prizes AS (
SELECT organization,
       COUNT(CASE WHEN rank = 1 THEN 1 END) AS first_places,
       COUNT(CASE WHEN rank = 2 THEN 1 END) AS second_places,
       COUNT(CASE WHEN rank = 3 THEN 1 END) AS third_places
FROM (
    SELECT wc.name organization,
           cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) AS score,
           ROW_NUMBER() OVER (PARTITION BY wc.name ORDER BY cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) DESC) AS rank
    FROM rsatom.competence c
    JOIN rsatom.employee e ON c.user_name = e.full_name
    JOIN rsatom.workspace_correction wc ON wc.name = e.workplace
) AS subquery
WHERE rank <= 3
GROUP BY organization
);