CREATE OR REPLACE VIEW division_prizes AS (
    SELECT division_name, COUNT(*) AS prize_places
FROM (
    SELECT d.name AS division_name,
           cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) AS score,
           ROW_NUMBER() OVER (PARTITION BY d.id ORDER BY cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) DESC) AS rank
    FROM rsatom.competence c
    JOIN rsatom.employee e ON c.user_name = e.full_name
    JOIN rsatom.workspace_correction wc ON wc.name = e.workplace
    JOIN rsatom.division d ON d.id = wc.division_id
    WHERE 1 = 1
) AS subquery
WHERE rank <= 3
GROUP BY division_name
);

CREATE OR REPLACE VIEW organization_prizes AS (
SELECT org_name, COUNT(*) AS prize_places
FROM (
    SELECT wc.name org_name,
           cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) AS score,
           ROW_NUMBER() OVER (PARTITION BY wc.name ORDER BY cast(replace((c.data::json->>'Баллы по ключевым навыкам'), ',','.') as numeric) DESC) AS rank
    FROM rsatom.competence c
    JOIN rsatom.employee e ON c.user_name = e.full_name
    JOIN rsatom.workspace_correction wc ON wc.name = e.workplace
) AS subquery
WHERE rank <= 3
GROUP BY org_name
);
