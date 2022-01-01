import React, {Dispatch, useEffect, useState} from 'react';
import {makeStyles, Theme} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import InputBase from '@material-ui/core/InputBase';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import TextField from "@material-ui/core/TextField";
import Box from "@material-ui/core/Box";
import * as userService from "../service/UserService";

const useStylesUsersPage = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(5)
    },
    cards: {
        display: 'flex',
        flexWrap: 'wrap'
    },
}));

export default function UsersPage() {
    const classes = useStylesUsersPage();
    const [users, setUsers] = useState<DTO.AppUserDto[]>([]);
    const [userForEdit, setUserForEdit] = useState<DTO.AppUserDto | null>(null);

    const loadUser = async (pattern: string) => {
        let users = await userService.loadUser(pattern);
        setUsers(users);
    }

    useEffect(function () {
        loadUser("");
    }, []);

    return (
        <div className={classes.root}>
            {userForEdit ? (<UserEdit user={userForEdit} setUserForEdit={setUserForEdit}/>) : (
                <div>
                    <SearchPanel onSearch={loadUser}/>
                    <div className={classes.cards}>
                        {
                            users.map((u) => <SimpleCard user={u} setUserForEdit={setUserForEdit}/>)
                        }
                    </div>
                </div>)
            }
        </div>
    );
}

const useStylesCard = makeStyles((theme) => ({
    root: {
        marginTop: theme.spacing(2)
    },
    bullet: {
        display: 'inline-block',
        marginRight: theme.spacing(1),
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
}));

type SimpleCardProps = {
    user: DTO.AppUserDto,
    setUserForEdit: Dispatch<DTO.AppUserDto | null>
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
                <Button size="small" onClick={() => props.setUserForEdit(props.user)}>Edit</Button>
                <Button size="small">Disabled</Button>
            </CardActions>
        </Card>
    );
}

const useStylesSearchPanel = makeStyles((theme) => ({
    root: {
        padding: '2px 4px',
        display: 'flex',
        alignItems: 'center',
        width: 400,
    },
    input: {
        marginLeft: theme.spacing(1),
        flex: 1,
    },
    iconButton: {
        padding: 10,
    }
}));

type SearchPanelProps = {
    onSearch: (pattern: string) => void;
};

function SearchPanel(props: SearchPanelProps) {
    const classes = useStylesSearchPanel();
    const [value, setValue] = useState("");

    const handleClick = (e: React.FormEvent) => {
        e.preventDefault();
        props.onSearch(value);
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            props.onSearch(value);
        }
    };

    return (
        <Paper component="form" className={classes.root}>
            <InputBase
                className={classes.input}
                placeholder="Search users"
                onKeyPress={handleKeyPress}
                onChange={e => setValue(e.target.value)}
            />
            <IconButton type="submit" className={classes.iconButton} aria-label="search" onClick={handleClick}>
                <SearchIcon/>
            </IconButton>
        </Paper>
    );
}

type UserEditProps = {
    user: DTO.AppUserDto,
    setUserForEdit: Dispatch<DTO.AppUserDto | null>
};

const useStylesUserEdit = makeStyles((theme: Theme) => ({
    formBox: {
        width: '100%', // Fix IE11 issue.
        marginTop: theme.spacing(3),
    },
    buttonSubmit: {
        margin: theme.spacing(2),
        width: 120
    }
}));

function UserEdit(props: UserEditProps) {
    const classes = useStylesUserEdit();
    const [name, setName] = useState(props.user.name);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();


    }

    return (
        <Box
            component="form"
            className={classes.formBox}
            onSubmit={handleSubmit}
        >
            <TextField
                required
                fullWidth
                label="Name"
                onChange={e => setName(e.target.value)}
                value={name}
            />

            <Button type="submit" variant="contained" className={classes.buttonSubmit}>
                Sign In
            </Button>
            <Button type="button" variant="contained" className={classes.buttonSubmit}
                    onClick={() => props.setUserForEdit(null)}>
                Cancel
            </Button>

        </Box>
    );
}
