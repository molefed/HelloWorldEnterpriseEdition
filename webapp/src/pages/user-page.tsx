import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import {api} from '../utils/http'

const useStylesUsersPage = makeStyles((theme) => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap'
    },
}));

export default function UsersPage() {
    const classes = useStylesUsersPage();
    const [users, setUsers] = useState<DTO.AppUserDto[]>([]);

    const loadUser = (pattern: string) => {
        api.post<DTO.SearchAppUserDTO, DTO.AppUserDto[]>("/users/search", {
            pattern: pattern
        } as DTO.SearchAppUserDTO)
            .then(users => setUsers(users));
    }

    useEffect(function () {
        loadUser("");
    }, []);

    return (
        <div className={classes.root}>
            {
                users.map((u) => <SimpleCard user={u}/>)
            }
        </div>
    );
}

const useStylesCard = makeStyles({
    root: {
        margin: 10
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
});

type SimpleCardProps = {
    user: DTO.AppUserDto
};

function SimpleCard(props: SimpleCardProps) {
    const classes = useStylesCard();

    return (
        <Card className={classes.root}>
            <CardContent>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                    {props.user.id}
                </Typography>
                <Typography variant="h5" component="h2">
                    {props.user.name}
                </Typography>
                <Typography className={classes.pos} color="textSecondary">
                    {props.user.roles.map((role, index) => (index ? ', ' : '') + role)}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small">Edit</Button>
                <Button size="small">Disabled</Button>
            </CardActions>
        </Card>
    );
}
